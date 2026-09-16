# Inventário de Equipamentos de Rede

Aplicativo Android desenvolvido em Kotlin como projeto final do módulo de
desenvolvimento Android.

## Identificação

- **Nome do aluno:** João Pedro Lopes Machado
- **Matrícula:** não informado
- **Data de entrega:** 9 de outubro de 2026 às 23:59

## Justificativa da escolha do tema

Curso Engenharia de Telecomunicações, e uma tarefa recorrente na área é manter o
registro dos equipamentos ativos de uma rede: roteadores, switches, access points,
seus endereços IP e onde estão fisicamente instalados. Escolhi transformar esse
controle — normalmente feito em planilhas — em um aplicativo móvel, porque o tema
une o conteúdo da formação Android ao domínio técnico do meu curso, e porque a
estrutura do problema (um cadastro com lista, detalhes e armazenamento local)
se encaixa bem nos requisitos da atividade.

## Descrição do funcionamento

O app mantém um inventário local de equipamentos de rede. Cada equipamento possui
nome, tipo, endereço IP, localização e observações.

1. **Tela de Lista** — mostra todos os equipamentos salvos, ordenados por nome.
   Um botão flutuante (+) abre o cadastro. Tocar em um item abre os detalhes.
   Quando não há nada cadastrado, a tela exibe uma mensagem orientando o usuário.
2. **Tela de Cadastro/Edição** — formulário com os campos do equipamento. O botão
   Salvar só é habilitado quando nome e tipo estão preenchidos. A mesma tela serve
   para criar um novo registro e para editar um existente.
3. **Tela de Detalhes** — exibe todos os dados do equipamento selecionado, com
   botão para editar e ícone na barra superior para excluir.

Os dados persistem no dispositivo: ao fechar e reabrir o app, tudo continua salvo.

## Requisitos técnicos atendidos

| Requisito | Onde está no projeto |
|---|---|
| Kotlin | Todo o código-fonte |
| 2+ telas em Jetpack Compose | `ui/screens/` — Lista, Cadastro e Detalhes (3 telas) |
| Navigation Compose | `ui/navigation/AppNavigation.kt` |
| Room com 1+ entidade | `data/Equipamento.kt`, `data/EquipamentoDao.kt`, `data/AppDatabase.kt` |
| MVVM | `viewmodel/EquipamentoViewModel.kt` |
| Separação em camadas | `data/` (dados) · `viewmodel/` (lógica) · `ui/` (interface) |

## Estrutura do projeto

```
com.joaopedro.inventarioredes
├── MainActivity.kt                 ponto de entrada; monta as camadas
├── data/
│   ├── Equipamento.kt              entidade Room (@Entity)
│   ├── EquipamentoDao.kt           operações de banco (@Dao)
│   ├── AppDatabase.kt              banco Room (singleton)
│   └── EquipamentoRepository.kt    isola a origem dos dados
├── viewmodel/
│   └── EquipamentoViewModel.kt     estado e regras; + Factory
└── ui/
    ├── navigation/AppNavigation.kt rotas entre as telas
    ├── screens/                    Lista, Cadastro, Detalhes
    └── theme/Theme.kt              cores do Material 3
```

## Fluxo dos dados (MVVM)

```
Tela (Compose)  →  ViewModel  →  Repository  →  DAO  →  Room (SQLite)
      ↑                                                      │
      └──────────── StateFlow / Flow (atualização) ──────────┘
```

A tela nunca acessa o banco diretamente: ela observa um `StateFlow` do ViewModel.
Quando um registro é inserido ou removido, o Room emite a nova lista pelo `Flow`
e a interface se recompõe sozinha.

## Como executar

1. Abrir o projeto no Android Studio (Ladybug ou superior).
2. Aguardar o Gradle Sync baixar as dependências.
3. Executar em um emulador ou dispositivo com Android 7.0 (API 24) ou superior.

## Observações

O banco de dados é criado automaticamente na primeira execução, com o nome
`inventario_redes.db`, no armazenamento privado do aplicativo.