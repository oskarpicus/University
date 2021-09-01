USE Biblioteca
go


-- 1) Lista tuturor autorilor cu carti imprumutate, impreuna cu varsta medie a imprumutatorilor
SELECT a.nume,a.prenume,AVG(i.varsta) [Varsta medie]
FROM Imprumutator i INNER JOIN Carte c ON i.CNP=c.CNP_Imprumutator INNER JOIN CarteAutor ca 
ON ca.cid=c.cid INNER JOIN Autor a ON a.autorid=ca.autorid
GROUP BY a.nume,a.prenume

-- 2) Salile care au carti de la autori romani cu mai mult de 120 de carti scrise (toti au peste 120 de carti)
SELECT DISTINCT s.denumire
FROM Sala s LEFT OUTER JOIN Carte c ON s.CNP_Angajat=c.CNP_Angajat INNER JOIN CarteAutor ca ON
ca.cid=c.cid INNER JOIN Autor a ON a.autorid=ca.autorid
WHERE a.tara in ('Romania')
GROUP BY s.denumire
HAVING MIN(a.nrCarti)>=120

-- 3) Tarile in care autorii lor au publicate cumulativ peste 100 de carti
SELECT a.tara,SUM(a.nrCarti) [Numar Carti]
FROM Autor a
GROUP BY a.tara
HAVING SUM(a.nrCarti)>=100

-- 4) Editurile, cu cel putin 700 carti publicate, care au carti in biblioteca, impreuna cu autorii care au carti la acele edituri
SELECT DISTINCT e.eid,e.nume as [Nume Editura],a.nume as [Nume Autor]
FROM Editura e INNER JOIN Carte c ON e.eid=c.eid INNER JOIN CarteAutor ca ON
ca.cid=c.cid INNER JOIN Autor a ON a.autorid=ca.autorid
WHERE e.nrPublicatii>=700

-- 5) Angajatii care au in subordine sali in care sunt peste 3 carti
SELECT ag.CNP,COUNT(ag.CNP) AS [Nr Carti]
FROM Angajat ag INNER JOIN Sala s ON ag.CNP=s.CNP_Angajat INNER JOIN Carte c ON s.CNP_Angajat=c.CNP_Angajat
GROUP BY ag.CNP
HAVING COUNT(ag.CNP)>=3

-- 6) Angajatii care au imprumutat carti
SELECT ang.nume,ang.prenume,c.titlu
FROM Angajat ang,Carte c INNER JOIN CarteAutor ca ON c.cid=ca.cid INNER JOIN Autor a ON ca.autorid=a.autorid
WHERE ang.CNP=c.CNP_Imprumutator 

-- altfel, cu subinterogari
SELECT ang.nume,ang.prenume
FROM Angajat ang
WHERE ang.CNP IN
(SELECT CNP_Imprumutator FROM Carte);

-- 7) Editurile care au carti de satira publicate, impreuna cu numarul lor
SELECT e.nume,COUNT(t.denumire) AS [Numar]
FROM Editura e INNER JOIN Carte c ON e.eid=c.eid INNER JOIN CarteTema ct ON c.cid=ct.cid INNER JOIN Tema t ON ct.tid=t.tid
WHERE t.denumire='satira'
GROUP BY t.denumire,e.nume

-- 8) Cartile grupate dupa tema, impreuna cu numarul cartilor de acea tema
SELECT t.denumire,COUNT(c.cid) as [Nr Carti]
FROM Tema t INNER JOIN CarteTema ct ON t.tid=ct.tid INNER JOIN Carte c ON ct.cid=c.cid
GROUP BY t.denumire

-- 9) Imprumutatorii care au luat carti care se incadreaza in realism
SELECT i.nume,i.prenume
FROM Imprumutator i INNER JOIN Carte c ON i.CNP=c.CNP_Imprumutator INNER JOIN CarteTema ct ON c.cid=ct.cid 
INNER JOIN Tema t ON ct.tid=t.tid
WHERE t.curent='realism'

-- 10) Imprumutatorii care au luat cel putin 2 carti
SELECT i.CNP,i.nume,i.prenume,COUNT(i.CNP) AS [Nr Carti]
FROM Imprumutator i INNER JOIN Carte c ON i.CNP=c.CNP_Imprumutator
GROUP BY i.CNP,i.nume,i.prenume
HAVING COUNT(i.CNP)>=2
