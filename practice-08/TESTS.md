# Практическая №8 — проверки состояний

Запуск: `javac -encoding UTF-8 -d out "@sources.txt"`, затем `java -cp out SelfCheck`.

| Сценарий | Ожидание |
|---|---|
| DRAFT -> submit -> approve -> complete после окончания | COMPLETED |
| approve из DRAFT | `IllegalStateException` |
| повторный submit | `IllegalStateException` |
| complete в сам день окончания | ошибка |
| cancel из COMPLETED | ошибка |
| reject с причиной 9 символов | ошибка |
| reject с причиной ≥ 10 символов | REJECTED, причина сохранена |
| approve из REJECTED | ошибка |
| cancel из DRAFT, SUBMITTED, APPROVED | CANCELLED |
| cancel из CANCELLED | ошибка |
| пустое имя сотрудника | ошибка submit |
| окончание раньше начала | ошибка submit |
| ровно 20 дней | успешно (вариант №3) |
| 21 день | ошибка (вариант №3) |

Проверки состояния и граничных дат выполняются автоматически в `SelfCheck`.

