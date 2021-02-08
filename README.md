# EasyPay_Mobile

## Indice
* [Intro](#intro)
* [Tecnologie e utilizzo](#tecnologie-e-utilizzo)
* [Panoramica](#panoramica)
* [Future Updates](#future-updates)

## Intro

EasyPay Mobile è la versione dedicata ad Android del metodo di pagamento EasyPay.
Questa applicazione è stata realizzata con lo scopo di fornire la possibilità di pagare presso commercianti con lo smartphone senza l'utilizzo di carte o contanti.
Il metodo di pagamento per il momento implementato è tramite QR Code.

L'applicazione permette la registrazione di un cliente, altrimenti, se già registrato l'utente può effettuare il login con le sue credenziali. 
Una volta loggato, l'applicazione è suddivisa in 3 sezioni:
* Il profilo dell'utente, in cui trova tutte le informazioni che lo riguardano solo in visualizzazione ;
* I movimenti, ovvero lo storico dei pagamenti e/o ricariche che sono stati effettuati sul proprio conto dal più recente in poi ;
* Il pagamento, sezione in cui viene generato un QRcode univoco per cliente collegato, che si interfaccia con il front-end dell'applicazione per portare a termine il pagamento. In questa sezione, è inoltre possibile visualizzare su una mappa, tramite il servizio esterno fornito da OpenStreetMap, i punti in cui è possibile usufruire di questo metodo di pagamento.

## Tecnologie e utilizzo

L'applicazione EasyPay Mobile è implementata interamente nel liguaggio Kotlin versione 1.4.21
ed è stata avviata con AndroidStudio versione 4.1.2.

Per installare l'applicazione bisogna collegarsi al seguente link : LINK PER FARE IL DOWNLOAD DELL'APK

## Panoramica
L'applicazione è basata sull'utilizzo di Activities, Fragments e di una NavigationView.
Un'activity come dice il termine, rappresentà un'attività, ovvero una funzionalità identificata all'interno dell'app.
Ci sono due Activity:
* Login Activity : ha il compito di fare eseguire il login all'user. Una volta eseguito il login, l'activity viene terminata.
* MainActivity : attività che si occupa della gestiona gestione delle interfacce.

Infatti, quando viene creata l'activity MainActivity, viene inizializzata anche una NavigationView di tipo BottomNavigationView.
Questa permette la visualizzazione delle 3 schermate diverse tramite un menu visualizzato nella parte bassa dello schermo una volta eseguito il login.

La navigazione all'interno dell'app è stata realizzata tramite i Fragments. 
Un Fragment rappresenta una parte riutilizzabile dell'interfaccia utente dell' app. Infatti ogni Fragment definisce e gestisce il proprio layout e può gestire i propri eventi di input. Tutti i fragment all'interno dell'app sono ospitati dall'activity MainActivity. 
Ciò che permette di navigare da un fragment ad un altro è il NavHostFragment definito all'interno dell'activity che li gestisce (MainActivity). 
Questo si occupa della navigazione tra i vari Fragment dell'app e lo si può trovare all'interno della cartella app/res/navigation e si chiama mobile_navigation.xml. 

All'interno del mobile_navigation sono state inserite le varie connessioni che ci possono essere tra i vari fragment e activities.

L'app quindi, viene lanciata tramite la MainActivity, che visualizza l'Home Fragment. Da qui è possibile seguire due strade:
* Registrazione --> comporta la compilazione di un form, che effettua alla conferma la registrazione del cliente e riporta all'interfaccia di login.
* Login --> viene generato un nuovo Intent, che permette di lanciare l'activity di Login. Questa esegue il login in modo asincrono tramite coroutines e al termine chiude l'activity. Durante il login viene salvato il Token restituito all'utente all'interno delle SharedPreferences, che permettono di salvare all'interno dell'applicazione delle informazioni utili al suo funzionamento,

Una volta effettuato il login, viene resa visibile la possibilità di navigare tra le interfacce, ed è stata impostata come schermata iniziale il Profilo utente.

Il contenuto dei vari Fragments viene ricaricato ad ogni nuova visualizzazione dello stesso. Ogni fragment ha un proprio Model che effettua le chiamate API per ricevere i dati. Le API sia GET che POST sono state realizzate tramite la creazione di instanze della classe HttpsURLConnection.

Quando viene effettuato il logout, il contenuto delle SharedPreferences viene rimosso e l'utente viene disconnesso dall'applicazione.

## Future Updates
* filtrare i movimenti per un periodo desiderato, per entrate / uscite;
* possibilità di aggiornare il proprio profilo;
* implementazione di nuovi metodi di pagamento e ricariche;
* rilevamento della posizione, al momento sono state settate coordinate geografiche statiche;
