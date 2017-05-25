# MCTiTunes
Требования к приложению:
- Один экран со строкой поиска и списком результатов поиска под ней.
- Пользователь должен иметь возможность ввести поисковый запрос и получить результаты поиска в виде списка, каждый из элементов которого должен иметь как минимум одно текстовое поле и одно изображение, соответствующие данному элементу.
- Поиск должен происходить асинхронно, не блокируя интерфейс.
- Поиск может происходить автоматически после ввода каждого нового символа, или по нажатию на некую кнопку поиска - без разницы.
- Любые неопределенности в ТЗ могут трактоваться не ваше усмотрение
- Язык исполнения - Java (без RxJava).
- Желательно написать несколько юнит-тестов (как минимум один из возможных - тест на корректность парсинга данных ответа от сервера)
