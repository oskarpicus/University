n = 3;
[A, b] = P3(n);

% eliminare Gaussiana
x = P1([A, b])

% descompunere LUP
x = P2_solveLup(A, b)

% descompunere Cholesky
x = P4_solveCholesky(A, b)
