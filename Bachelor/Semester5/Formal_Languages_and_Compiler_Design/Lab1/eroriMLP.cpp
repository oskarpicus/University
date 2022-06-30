#include<iostream>
int main(){
	int x;
	int y;
	std::cin>>x>>y;
	int minim;
	minim = (x < y ? x : y);
	std::cout<<minim;
	return 0;
}
