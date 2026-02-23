# Control+

Aplicativo Android de controle pessoal que unifica **controle financeiro** e **controle de tempo** em um painel inteligente.

## MVP entregue
- Dashboard com indicador central de controle diário.
- Card financeiro com cálculo automático de limite diário.
- Card de tempo com limites por app e progresso de uso.
- Card de metas.
- Calendário inteligente com lembretes.
- Heatmap mensal de gastos.
- Estrutura de dados preparada para expansão (Room entities).

## Arquitetura
- `data`: entidades e persistência.
- `domain`: regras de negócio (score e limite diário).
- `ui`: telas Compose com tema dark neon.

## Fórmula de limite diário
`limite_hoje = (orcamento_mensal - gasto_ate_agora) / dias_restantes`

## Próximos passos
- Integração real com UsageStatsManager e Accessibility para bloqueio.
- DAO + Repository + casos de uso para persistência completa.
- Sincronização com APIs bancárias.
