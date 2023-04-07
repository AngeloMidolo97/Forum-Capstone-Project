Forum App
Questa applicazione è un forum dove gli utenti possono registrarsi e fare domande alla community.

Tecnologie utilizzate
L'applicazione è stata sviluppata utilizzando il framework Angular per il frontend e il linguaggio Java con il framework Spring per il backend.

Funzionalità
Le funzionalità principali dell'applicazione sono:

Registrazione utente
Login utente
Visualizzazione delle domande degli utenti
Inserimento di una nuova domanda
Risposta ad una domanda
Installazione
Per eseguire l'applicazione è necessario installare Node.js, Angular CLI e Java 11. Una volta installate queste dipendenze, clonare il repository e seguire i seguenti passi:


Frontend: https://github.com/AngeloMidolo97/ForumApp
Aprire una finestra del terminale nella cartella del progetto e digitare il comando cd frontend per spostarsi nella cartella del frontend
Digitare il comando npm install per installare le dipendenze del progetto
Digitare il comando ng serve per avviare il server di sviluppo del frontend. L'applicazione sarà disponibile all'indirizzo http://localhost:4200/.


Backend : https://github.com/AngeloMidolo97/Forum-Capstone-Project
Aprire un'altra finestra del terminale nella cartella del progetto e digitare il comando cd backend per spostarsi nella cartella del backend
Digitare il comando ./mvnw spring-boot:run per avviare il server di sviluppo del backend. Il server sarà disponibile all'indirizzo http://localhost:8080/.
Configurazione
Il backend dell'applicazione utilizza una base di dati MySQL. Per configurare la connessione al database, modificare il file application.properties nella cartella backend/src/main/resources con i propri parametri di connessione.
