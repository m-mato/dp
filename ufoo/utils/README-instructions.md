## Inštrukcie pre testovanie

### Funkcionalita
Aplikácia, ktorá je dostupná po spustení WAR súboru server-1.0-SNAPSHOT.war
poskytuje nasledujúcu funkcionalitu:

* 4 testovacie zdroje pre testovanie algoritmu identifikátoru (simulujúce reálnu
  aplikáciu) s rôznou dĺžkou trvania:
  * localhost:8080/test/short -- od 100 do 700 milisekúnd
  * localhost:8080/test/medium -- od 500 do 1500 milisekúnd
  * localhost:8080/test/long -- od 1000 do 5000 milisekúnd
  * localhost:8080/test/sim -- od 100 do 5000 milisekúnd

* UI pre analýzu a vizualizáciu dát spracovaných identifikačným algoritmom
  na adrese localhost:8080/analysis, konkrétne:
  
  * localhost:8080/analysis/histogram -- Histogram počtu vzdialeností spracovaných
    požiadaviek. OS x = vzdialenosť. OS y = počet.
  
  * localhost:8080/analysis/flow -- Vzdialenosť požiadaviek simulácie v čase.
    Os x = čas. Os y = vzdialenosť.

Buffer pre zápis dát pre analýzu je 10 požiadavok. Zobrazenie v grafoch je
teda možné až po prekročení tohto počtu !

Pri opätovnom spustení aplikácie sú všetky dáta zmazané.
(Pre prípadný export dát do raw csv formátu je možné použiť metódu dostupnú
na localhost:8080/analysis/getCSV)

### Spustenie

Aplikáciu je možné s WAR súboru spustiť na porte 8080 nasledujúcim príkazom:
> java -jar server-1.0-SNAPSHOT.war -Dserver.port=8080

### Testovanie

Pre testovanie funkcionality algoritmu na určenie podobnosti požiadaviek stačí
zasielať ľubovoľné HTTP POST požiadavky na vyššie uvedené zdroje hostiteľskej
aplikácie.

Jednou z možností je použiť predpripravený test aplikácie v adresári projektu
example/server, konkétne v /src/test/java, ktorý obsahuje implementáciu klienta
so spustiteľnou triedou StartStandardClient.
Po spustení jej metódy main je na server (ak samozrejme beží na localhost:8080)
odoslaných 500 náhodne generovaných požiadaviek.

Priebeh testovania je následne možné sledovať pomocou štatistík na URL popísaných
vyššie (localhost:8080/analysis).