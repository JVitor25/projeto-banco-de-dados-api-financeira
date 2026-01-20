import requests
import json
import random
from datetime import datetime, date

# =================================================================================
# CONFIGURAÇÃO
# =================================================================================
BASE_URL = "http://localhost:8080/api"

# =================================================================================
# DADOS FIXOS PARA O CENÁRIO
# =================================================================================
# Formato ISO 8601 completo para LocalDateTime (ex: 2023-12-01T10:00:00)
DATA_HORA_ATUAL = datetime.now().strftime("%Y-%m-%dT%H:%M:%S")

USUARIO_DATA = {
    "nome": "João Silva",
    "email": "joao.silva@exemplo.com",
    "senha": "123",
    "dataCadastro": DATA_HORA_ATUAL,
    "dataAtualizacao": DATA_HORA_ATUAL
}

CONTA_DATA_1 = {"nome": "Conta Nubank", "tipo": "CORRENTE", "saldoInicial": 100.00}
CONTA_DATA_2 = {"nome": "Carteira Física", "tipo": "DINHEIRO", "saldoInicial": 50.00}

CARTAO_DATA = {
    "nome": "Nubank Platinum",
    "limite": 5000.00,
    "diaFechamento": 1,
    "diaVencimento": 10,
    "bandeira": "MASTERCARD",
    "validadeMes": 12,
    "validadeAno": 2030
}

FATURA_DATA = {
    "mesReferencia": 12,
    "anoReferencia": 2023,
    "dataFechamento": "2023-12-01",
    "dataVencimento": "2023-12-10",
    "valorTotal": 0.00,
    "statusPagamento": "ABERTA"
}

# =================================================================================
# FUNÇÕES AUXILIARES
# =================================================================================
def post(endpoint, data):
    try:
        response = requests.post(f"{BASE_URL}/{endpoint}", json=data)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        print(f"❌ Erro em POST /{endpoint}: {e}")
        if 'response' in locals() and response is not None:
            print(f"   Detalhes: {response.text}")
        return None

def get_all(endpoint):
    try:
        response = requests.get(f"{BASE_URL}/{endpoint}")
        return response.json()
    except:
        return []

def buscar_id_grupo_por_nome(nome_parcial):
    """Busca um grupo pelo nome para não precisarmos chutar IDs"""
    grupos = get_all("grupos")
    # Função recursiva para buscar na árvore de grupos
    def buscar_recursivo(lista_grupos):
        for g in lista_grupos:
            if nome_parcial.lower() in g['nome'].lower():
                return g['idGrupo']
            # Se tiver filhos (dependendo de como sua API retorna, pode precisar de outra chamada)
            # Assumindo que a API retorna lista plana ou precisamos iterar
        return None
    
    # Simplificação: A maioria das APIs retorna lista plana ou paginada. 
    # Vou iterar sobre o que vier.
    for g in grupos:
        if nome_parcial.lower() in g['nome'].lower():
            return g['idGrupo']
    return None

# =================================================================================
# FLUXO PRINCIPAL
# =================================================================================
def main():
    print("🚀 INICIANDO POPULAÇÃO DO BANCO DE DADOS...")

    # 1. CRIAR USUÁRIO
    print("\n👤 Criando Usuário...")
    usuario = post("usuarios", USUARIO_DATA)
    if not usuario: return
    id_usuario = usuario['idUsuario']
    print(f"   ✅ Usuário criado: {usuario['nome']} (ID: {id_usuario})")

    # 2. CRIAR CONTAS
    print("\nbank Criando Contas...")
    # Vincula ao usuário
    CONTA_DATA_1['usuario'] = {"idUsuario": id_usuario}
    CONTA_DATA_2['usuario'] = {"idUsuario": id_usuario}
    
    conta1 = post("contas", CONTA_DATA_1)
    conta2 = post("contas", CONTA_DATA_2)
    
    if not conta1 or not conta2: return
    id_conta_banco = conta1['idConta']
    id_conta_carteira = conta2['idConta']
    print(f"   ✅ Contas criadas: IDs {id_conta_banco} e {id_conta_carteira}")

    # 3. CRIAR CARTÃO
    print("\n💳 Criando Cartão de Crédito...")
    CARTAO_DATA['usuario'] = {"idUsuario": id_usuario}
    cartao = post("cartoes-credito", CARTAO_DATA)
    if not cartao: return
    id_cartao = cartao['idCartao']
    print(f"   ✅ Cartão criado: {cartao['nome']} (ID: {id_cartao})")

    # 4. CRIAR FATURA
    print("\n📄 Criando Fatura...")
    FATURA_DATA['cartaoCredito'] = {"idCartao": id_cartao}
    fatura = post("faturas", FATURA_DATA)
    if not fatura: 
        print("   ⚠️ Falha ao criar fatura (talvez já exista). Seguindo sem fatura.")
        id_fatura = None
    else:
        id_fatura = fatura['idFatura']
        print(f"   ✅ Fatura criada: ID {id_fatura}")

    # 5. BUSCAR GRUPOS (Assume que você já rodou o popular_grupos.py)
    print("\n🔍 Buscando IDs de Grupos...")
    id_salario = buscar_id_grupo_por_nome("Salário") or 1
    id_alimentacao = buscar_id_grupo_por_nome("Restaurantes") or 2
    id_transporte = buscar_id_grupo_por_nome("Aplicativos") or 3
    id_transferencia = buscar_id_grupo_por_nome("Transferência") or 4
    
    print(f"   Grupos encontrados: Salário({id_salario}), Alimentação({id_alimentacao})")

    # 6. CRIAR LANÇAMENTOS
    print("\n💸 Criando Lançamentos...")

    lancamentos = [
        # RECEITA: Salário
        {
            "valor": 3500.00,
            "tipo": "RECEITA",
            "descricao": "Salário Mensal - Empresa X",
            "data": str(date.today()),
            "usuario": {"idUsuario": id_usuario},
            "conta": {"idConta": id_conta_banco},
            "grupo": {"idGrupo": id_salario}
        },
        # DESPESA: Almoço (Dinheiro)
        {
            "valor": 45.90, # O Backend vai converter para negativo automaticamente
            "tipo": "DESPESA",
            "descricao": "Almoço Restaurante Mineiro",
            "data": str(date.today()),
            "usuario": {"idUsuario": id_usuario},
            "conta": {"idConta": id_conta_carteira},
            "grupo": {"idGrupo": id_alimentacao}
        },
        # DESPESA: Uber (Cartão de Crédito)
        {
            "valor": 22.50,
            "tipo": "DESPESA",
            "descricao": "Uber para o trabalho",
            "data": str(date.today()),
            "usuario": {"idUsuario": id_usuario},
            "conta": {"idConta": id_conta_banco}, # Vincula à conta principal
            "grupo": {"idGrupo": id_transporte},
            "cartao": {"idCartao": id_cartao},
            "fatura": {"idFatura": id_fatura} if id_fatura else None
        },
        # TRANSFERENCIA (Saída)
        {
            "valor": 100.00,
            "tipo": "TRANSFERENCIA_SAIDA",
            "descricao": "Saque para Carteira",
            "data": str(date.today()),
            "usuario": {"idUsuario": id_usuario},
            "conta": {"idConta": id_conta_banco},
            "grupo": {"idGrupo": id_transferencia}
        },
        # TRANSFERENCIA (Entrada - Contrapartida da anterior)
        {
            "valor": 100.00,
            "tipo": "TRANSFERENCIA_ENTRADA",
            "descricao": "Depósito vindo do Banco",
            "data": str(date.today()),
            "usuario": {"idUsuario": id_usuario},
            "conta": {"idConta": id_conta_carteira},
            "grupo": {"idGrupo": id_transferencia}
        }
    ]

    for l in lancamentos:
        # Remove campos nulos (ex: fatura se não tiver)
        payload = {k: v for k, v in l.items() if v is not None}
        
        res = post("lancamentos", payload)
        if res:
            print(f"   ✅ Lançamento criado: {res['descricao']} | Valor: {res['valor']} | Tipo: {res['tipo']}")

    print("\n✨ POPULAÇÃO CONCLUÍDA COM SUCESSO!")

if __name__ == "__main__":
    main()
