function out=Lab3_P2(p,n,m)
clf; grid on; hold on;
x=binornd(n,p,1,m);
N=histc(x,0:n);
bar(0:n,N/m,'hist','FaceColor','b');
bar(0:n,binopdf(0:n,n,p),'FaceColor','y');
legend('estimated probabilities','theoretical probabilities');
out=0;
endfunction
