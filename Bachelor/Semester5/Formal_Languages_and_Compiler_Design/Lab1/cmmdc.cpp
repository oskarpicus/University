#include<iostream>
int main(){
	std::cout<<"Dati numerele\n";
	int x;
	int y;
	std::cin>>x;
	std::cin>>y;
	while(y!=0){
		int r;
		r = x%y;
		x = y;
		y = r;
	}
	std::cout<<x;
	std::cout<<"\n";
	return 0;
}
