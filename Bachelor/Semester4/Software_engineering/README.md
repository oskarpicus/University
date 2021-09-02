# ISS

Bug tracker implemented in Java, following the UML diagrams and models developed over the course of the semester (available in `Diagrams.mdj`). 
This is a clone of the [ISS](https://github.com/oskarpicus/ISS) repository.
Full problem statement in available below in Romanian.

## URMARIRE BUG-URI

O firma producatoare de software pune la dispozitia programatorilor si verificatorilor sai un sistem prin care acestia pot sa comunice electronic. Astfel, fiecare dintre angajatii mentionati are la dispoziție un terminal prin care: 
- *verificatorul*  poate  înregistra  un  bug,  dându-i  o  denumire, o severitate  si  o  descriere;  imediat  dupa înregistrarea bug-ului, toti  programatorii vad  lista bug-urilor  actualizata cu obiectul nou introdus;
- *programatorul* vizualizeaza lista bug-urilor; de asemenea, programatorul poate selecta un bug din lista si poate declansa un buton prin care declara ca bug-ul a fost eliminat, caz în care bug-ul este scos din lista tuturor programatorilor. 
- autentificare verificator si programator
- posibilitatea precizarii daca la un bug lucreaza deja un programator sau nu - toti programatorii vad lista bug-urilor actualizata
- filtrare bug-uri dupa severitatea lor
- *verificatorul* poate vizualiza bug-urile adaugate de el

