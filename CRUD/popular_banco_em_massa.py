import requests
import json
import random
import calendar
from datetime import datetime, timedelta, date

# =================================================================================
# CONFIGURAÇÃO
# =================================================================================
BASE_URL = "http://localhost:8080/api"
ANO_INICIO = 2023
MES_INICIO = 1

# =================================================================================
# DADOS GERADORES (CATÁLOGOS)
# =================================================================================
NOMES_ESTABELECIMENTOS = {
    "Alimentação": ["Supermercado BH", "Carrefour", "Padaria Pão Quente", "Restaurante da Tia", "iFood", "Burger King", "Pizzaria"],
    "Transporte": ["Uber Trip", "Posto Ipiranga", "Posto Shell", "99 Pop", "Recarga Bilhete Único", "Mecânica Simas"],
    "Lazer e Estilo de Vida": ["Netflix", "Spotify", "Cinema Kinoplex", "Steam Games", "Amazon Prime", "Ingresso.com"],
    "Saúde": ["Drogasil", "Drogaria Araujo", "Consulta Dr. Consulta", "Laboratório Hermes"],
    "Moradia": ["Leroy Merlin", "Camicado", "Internet Claro", "CEMIG", "COPASA"]
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
        print(f"❌ Erro POST {endpoint}: {e}")
        return None

def get_all(endpoint):
    try:
        return requests.get(f"{BASE_URL}/{endpoint}").json()
    except:
        return []

def get_random_datetime(year, month):
    """Gera uma data/hora aleatória dentro de um mês específico"""
    last_day = calendar.monthrange(year, month)[1]
    day = random.randint(1, last_day)
    hour = random.randint(8, 22)
    minute = random.randint(0, 59)
    return datetime(year, month, day, hour, minute, 0).strftime("%Y-%m-%dT%H:%M:%S")

def get_fixed_datetime(year, month, day):
    """Gera uma data/hora fixa"""
    # Garante que o dia existe (ex: não existe 30 de fevereiro)
    last_day = calendar.monthrange(year, month)[1]
    day = min(day, last_day) 
    return datetime(year, month, day, 10, 0, 0).strftime("%Y-%m-%dT%H:%M:%S")

# =================================================================================
# FLUXO PRINCIPAL
# =================================================================================
def main():
    print("🚀 INICIANDO GERAÇÃO DE DADOS EM MASSA...")

    # 1. CRIAR USUÁRIO NOVO
    print("\n👤 Criando Usuário 'Ana Souza'...")
    usuario = post("usuarios", {
        "nome": "Ana Souza",
        "email": f"ana.souza.{random.randint(1000,9999)}@email.com",
        "senha": "123",
        "dataCadastro": datetime.now().strftime("%Y-%m-%dT%H:%M:%S"),
        "dataAtualizacao": datetime.now().strftime("%Y-%m-%dT%H:%M:%S")
    })
    if not usuario: return
    id_usuario = usuario['idUsuario']

    # 2. CRIAR CONTAS
    print("🏦 Criando Contas...")
    conta_corrente = post("contas", {"nome": "Conta Principal", "tipo": "CORRENTE", "saldoInicial": 0, "usuario": {"idUsuario": id_usuario}})
    conta_poupanca = post("contas", {"nome": "Reserva Emergência", "tipo": "POUPANCA", "saldoInicial": 5000, "usuario": {"idUsuario": id_usuario}})
    conta_carteira = post("contas", {"nome": "Dinheiro Vivo", "tipo": "DINHEIRO", "saldoInicial": 200, "usuario": {"idUsuario": id_usuario}})
    
    id_cc = conta_corrente['idConta']
    id_cp = conta_poupanca['idConta']
    id_carteira = conta_carteira['idConta']

    # 3. CRIAR CARTÃO
    print("💳 Criando Cartão...")
    cartao = post("cartoes-credito", {
        "nome": "Nubank Ultravioleta",
        "limite": 15000.00,
        "diaFechamento": 1,
        "diaVencimento": 10,
        "bandeira": "MASTERCARD",
        "validadeMes": 10,
        "validadeAno": 2029,
        "usuario": {"idUsuario": id_usuario}
    })
    id_cartao = cartao['idCartao']

    # 4. MAPEAMENTO DE GRUPOS (Busca IDs reais do banco)
    print("🔍 Mapeando Grupos...")
    todos_grupos = get_all("grupos")
    mapa_grupos = {} # {'Salário': 1, 'Supermercado': 5, ...}
    
    for g in todos_grupos:
        mapa_grupos[g['nome']] = g['idGrupo']
        # Tenta mapear subgrupos se existirem na resposta, senão mapeia pelo nome principal
    
    # Função helper para pegar ID ou usar um default
    def get_grupo_id(nome_parcial):
        for nome, id_g in mapa_grupos.items():
            if nome_parcial.lower() in nome.lower():
                return id_g
        return todos_grupos[0]['idGrupo'] if todos_grupos else 1

    id_salario = get_grupo_id("Salário")
    id_moradia = get_grupo_id("Moradia")
    id_transf = get_grupo_id("Transferência")

    # 5. LOOP TEMPORAL (Mês a Mês)
    data_atual = date.today()
    ano_iter = ANO_INICIO
    mes_iter = MES_INICIO

    while (ano_iter < data_atual.year) or (ano_iter == data_atual.year and mes_iter <= data_atual.month):
        print(f"\n📅 Processando: {mes_iter}/{ano_iter}...")

        # A. CRIAR FATURA DO MÊS
        fatura = post("faturas", {
            "mesReferencia": mes_iter,
            "anoReferencia": ano_iter,
            "dataFechamento": get_fixed_datetime(ano_iter, mes_iter, 1),
            "dataVencimento": get_fixed_datetime(ano_iter, mes_iter, 10),
            "valorTotal": 0.00,
            "statusPagamento": "FECHADA" if (ano_iter < data_atual.year or mes_iter < data_atual.month) else "ABERTA",
            "cartaoCredito": {"idCartao": id_cartao}
        })
        id_fatura = fatura['idFatura'] if fatura else None

        # B. RECEITA: SALÁRIO (Dia 5)
        post("lancamentos", {
            "valor": 6500.00,
            "tipo": "RECEITA",
            "descricao": "Salário Mensal",
            "data": get_fixed_datetime(ano_iter, mes_iter, 5),
            "usuario": {"idUsuario": id_usuario},
            "conta": {"idConta": id_cc},
            "grupo": {"idGrupo": id_salario}
        })

        # C. DESPESAS FIXAS (Dia 10)
        despesas_fixas = [
            ("Aluguel", 1800.00, id_moradia),
            ("Condomínio", 450.00, id_moradia),
            ("Internet", 120.00, get_grupo_id("Consumo"))
        ]
        for desc, valor, grp in despesas_fixas:
            post("lancamentos", {
                "valor": valor,
                "tipo": "DESPESA",
                "descricao": desc,
                "data": get_fixed_datetime(ano_iter, mes_iter, 10),
                "usuario": {"idUsuario": id_usuario},
                "conta": {"idConta": id_cc},
                "grupo": {"idGrupo": grp}
            })

        # D. DESPESAS VARIÁVEIS (Aleatórias durante o mês)
        qtd_lancamentos = random.randint(15, 30)
        for _ in range(qtd_lancamentos):
            categoria_nome = random.choice(list(NOMES_ESTABELECIMENTOS.keys()))
            estabelecimento = random.choice(NOMES_ESTABELECIMENTOS[categoria_nome])
            grupo_id = get_grupo_id(categoria_nome)
            
            valor = round(random.uniform(15.00, 300.00), 2)
            usar_credito = random.choice([True, False])
            
            payload = {
                "valor": valor,
                "tipo": "DESPESA",
                "descricao": estabelecimento,
                "data": get_random_datetime(ano_iter, mes_iter),
                "usuario": {"idUsuario": id_usuario},
                "grupo": {"idGrupo": grupo_id}
            }

            if usar_credito and id_fatura:
                payload["conta"] = {"idConta": id_cc} # Vincula à conta principal
                payload["cartao"] = {"idCartao": id_cartao}
                payload["fatura"] = {"idFatura": id_fatura}
                payload["descricao"] += " (Crédito)"
            else:
                # Débito ou Dinheiro
                conta_escolhida = id_cc if random.random() > 0.3 else id_carteira
                payload["conta"] = {"idConta": conta_escolhida}

            post("lancamentos", payload)

        # E. INVESTIMENTO (Se sobrar dinheiro no fim do mês - Dia 28)
        if random.random() > 0.2: # 80% de chance de investir
            valor_invest = round(random.uniform(200, 1000), 2)
            # Saída da CC
            post("lancamentos", {
                "valor": valor_invest,
                "tipo": "TRANSFERENCIA_SAIDA",
                "descricao": "Aplicação Poupança",
                "data": get_fixed_datetime(ano_iter, mes_iter, 28),
                "usuario": {"idUsuario": id_usuario},
                "conta": {"idConta": id_cc},
                "grupo": {"idGrupo": id_transf}
            })
            # Entrada na Poupança
            post("lancamentos", {
                "valor": valor_invest,
                "tipo": "TRANSFERENCIA_ENTRADA",
                "descricao": "Aplicação Poupança",
                "data": get_fixed_datetime(ano_iter, mes_iter, 28),
                "usuario": {"idUsuario": id_usuario},
                "conta": {"idConta": id_cp},
                "grupo": {"idGrupo": id_transf}
            })

        # Avança o mês
        mes_iter += 1
        if mes_iter > 12:
            mes_iter = 1
            ano_iter += 1

    print("\n✨ GERAÇÃO EM MASSA CONCLUÍDA!")

if __name__ == "__main__":
    main()
