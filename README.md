# 🎬 Cinema - Sistema de Totem de Cinema
> Sistema desktop completo de autoatendimento para venda de ingressos de cinema, desenvolvido em Java com interface gráfica Java Swing e persistência de dados em arquivo binário.

> ## 📌 Sobre o Projeto
O **Cinema** simula a experiência de um terminal/totem físico de cinema. O objetivo principal do projeto foi construir uma aplicação orientada a objetos (POO) robusta, aplicando padrões de projeto, separação clara de responsabilidades (MVC e DAO) e suporte a diferentes categorias de ingressos, mapas interativos de assentos e persistência local.

## ✨ Funcionalidades
- **Catálogo de Filmes:** Exibição dinâmica de filmes em cartaz com sinopse, duração, gênero, classificação etária e poster.
- **Sessões e Salas:** Consulta de horários e valores por sala e sessão.
- **Mapa Interativo de Poltronas:**
  - Identificação de fileiras por letras (A, B, C, D).
  - Assentos categorizados visualmente por status: **Livre (Verde)**, **Ocupado (Cinza/X)** e **Selecionado (Amarelo)**.
- **Tipos de Ingresso:** Suporte para combinação flexível de ingressos do tipo **Inteira** e **Meia-Entrada**.
- **Carrinho e Checkout:** Atualização do valor total em tempo real e emissão de comprovante detalhado de compra.
- **Persistência Binária:** Salvamento automático do estado das sessões, filmes e vendas no arquivo `cinema_dados.dat`.

- ## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Java
- **Interface Gráfica:** Java Swing (AWT/Swing)
- **Paradigma:** Programação Orientada a Objetos (POO)
- **Persistência de Dados:** Serialização nativa do Java (`ObjectOutputStream` / `ObjectInputStream`)
- **IDE:** IntelliJ IDEA
- **Controle de Versão:** Git e GitHub

- ## 🏗️ Arquitetura e Estrutura de Pacotes
O projeto adota uma arquitetura em camadas bem definida para garantir legibilidade e manutenção do código:
```text
br.com.cinema/
├── controller/     # Intermediador entre a View e os Services
├── dao/            # Data Access Object para leitura/escrita em arquivo binário
├── model/          # Entidades do domínio (Filme, Sessao, Assento, Ingresso, Venda, Sala, etc.)
├── service/        # Regras de negócio e validações
└── view/           # Telas da interface gráfica em Swing (TelaPrincipal, TelaMapaSala)
```

## 📱 Demonstração do Projeto

<p align="center">
  <img src="https://github.com/user-attachments/assets/e9ce12b4-02de-4a19-a6fb-14142a1ac352" alt="Demonstração Cinema" width="100%" />
</p>

---

## 💻 Como Executar o Projeto Localmente

### Pré-requisitos
* **Java Development Kit (JDK):** Versão 17 ou superior instalada.
* **IDE Recomendada:** IntelliJ IDEA, Eclipse ou VS Code.

### Passo a Passo
1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/GabrielCagliariLuz/cinema-totem-system.git](https://github.com/GabrielCagliariLuz/cinema-totem-system.git)
   ```
2. **Rode o arquivo Main na sua IDE**

