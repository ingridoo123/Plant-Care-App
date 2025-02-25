# Plant-Care-App
Jest to aplikacja moblina na system operacyjny Android z wykorzystaniem języka obiektowego Kotlin.

Jest to aplikacja do zarządzania domowymi (ale nie tylko) roślinami. Wykorzystałem wiele rozbudowanych funkcji oraz zastosowań języka obiektowego w tym projekciem, takich jak: dziedziczenie w celu stworzenia bazowej klasy dla różnych typów roślin czy polimorfizm do obsługi specyficznych akcji dla różnych kategorii roślin (np. kwiatów, sukulentów, roślin tropikalnych). W projekcie użyłem aktualnych trendów w tworzeniu natywnych aplikacji dla Androida.

Do pobierania danych o roślinach aplikacja wykorzystuje stworzony przeze mnie mały serwer z danymi. Użyłem do tego popularnego framework'a backend'owego do języka Kotlin o nazwie Ktor. Poprzez połączenie internetowe aplikacja zbiera dane z serwera, a następnie zapisuje je na loklanej pamięci telefonu przez określony czas. Umożliwa to płynne działanie aplikacji oraz możliwość jej działania w trybie offline lub przy wyłączonym serwerze. 

Do przechowywania oraz operacji na zebranych danych zdecydowałem się użyć SQLite w wersji kompatybilnej z Android Studio. 

Postanowiłem również dodać możliwość utworzenia konta przy pierwszym starcie aplikacji poprzez biblioteke "Google Firebase", która  umożliwia szybkie i bezpieczne uwierzytelnianie użytkowników za pomocą metod takich jak e-mail i hasło, a także wsparcie dla zewnętrznych dostawców logowania, np. Google lub Facebook. Użytkownik po utworzeniu konta, będzie mógł się na nie zalogować zgodnie z podanymi przez niego wcześniej danymi np. username oraz password. Wygląd segementów logowania oraz rejestracji lekko rózni się od pozostałego UI aplikacji, ponieważ dodałem te funkcjonalności po napisaniu głownego trzonu aplikacji i nie chciałem go zmieniać, co było spowodowane tym że znalazłem nowoczesną biblioteke dotworzenia interfejsu, która wymagała innej struktury kodu w projekcie.

Aplikacja ma przyjemne dla oka UI oraz responsywne UX. Do inspiracji jeśli chodzi o stronę wizualną aplikacji posłużyły mi rożne concept art'y znalezione w internecie, jak może wyglądać aplikacja w stylu "Plant Care"/"Plant Management". 

![image alt](https://github.com/ingridoo123/Plant-Care-App/blob/5fb2700cd20c65e4805ae5705457f5d2c607a8ef/Untitled.png)
![image alt](https://github.com/ingridoo123/Plant-Care-App/blob/5fb2700cd20c65e4805ae5705457f5d2c607a8ef/Desktop%20-%202.png)
![image alt](https://github.com/ingridoo123/Plant-Care-App/blob/5fb2700cd20c65e4805ae5705457f5d2c607a8ef/Desktop%20-%203.png)
![image_alt](https://github.com/ingridoo123/Plant-Care-App/blob/5fb2700cd20c65e4805ae5705457f5d2c607a8ef/Desktop%20-%204.png)


