#include<iostream>
int main(){
	std::cout<<"Dati raza\n";
	float raza;
	std::cin>>raza;
	
	float perimetru;
	perimetru=2*3.14;
	perimetru=perimetru*raza;
	std::cout<<"Perimetrul: ";
	std::cout<<perimetru;
	std::cout<<"\n";

	float arie;
	arie=3.14*raza;
	arie=arie*raza;
	std::cout<<"Aria: ";
	std::cout<<arie;
	std::cout<<"\n";
	return 0;
}
