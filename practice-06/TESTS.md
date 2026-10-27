# Практическая №6 — таблица решений и границы

Запуск: `javac -encoding UTF-8 -d out "@sources.txt"`, затем `java -cp out SelfCheck`.

| Сумма | Срочно | Риск | Личные данные | Маршрут |
|---:|:---:|---|:---:|---|
| 0 | Нет | LOW | Да | SECURITY_REVIEW |
| 600000 | Да | HIGH | Да | SECURITY_REVIEW |
| 600000 | Нет | HIGH | Нет | DIRECTOR_REVIEW |
| 499999 | Нет | LOW | Нет | STANDARD |
| 500000 | Нет | MEDIUM | Нет | FINANCE_REVIEW |
| 500001 | Нет | LOW | Нет | FINANCE_REVIEW |
| 19999 | Да | LOW | Нет | FAST_TRACK |
| 20000 | Да | LOW | Нет | STANDARD (вариант №3) |
| 499999 | Да | MEDIUM | Нет | STANDARD (вариант №3) |
| 100 | Да | HIGH | Нет | DIRECTOR_REVIEW |

Проверяются также отрицательная сумма и `null` вместо риска. Всего `SelfCheck` содержит 13 сценариев.

