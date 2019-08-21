### Dodanie datasource

1. Znajdź po prawej stronie zakładkę `Database`.

![create datasource](assets/datasource/_1.png)


2. Kliknij na ikonę `+` i wybierz `Datasource` > `Mysql`
3. Wprawadź nazwę użytkownika `root` i hasło jakie zostało wprowadzone podczas instalacji.

![add datasource](assets/datasource/_2.png)

4. Kliknij na `Test Connection`. Jeżeli test się powiedzie to zostanie wyświetlony zielony znaczek  ✔.
5. Kliknij na `Apply` a nowe źródło powinno pojawić się po prawej stronie.

![finish](assets/datasource/_3.png)

Jeżeli `Test Connection` zwrócic błąd, to spróbuj w polu `URL` dodaj parametry:
`?useTimezone=true&serverTimezone=UTC`. Cały URL będzie wyglądał podobnie do 
`jdbc:mysql://localhost:3306?useTimezone=true&serverTImezone=UTC`.
  
Następnie w razie niepowodzenia spróbuj wprowadzić jako `Host` *0.0.0.0* zamiast *localhost*.
Jeżeli nawet to nie pomaga, to spróbuj zainstalować jeszcze raz bazę danych.
