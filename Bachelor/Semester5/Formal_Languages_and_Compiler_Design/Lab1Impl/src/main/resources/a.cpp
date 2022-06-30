#include<iostream>
int main(){
        int n;
        int s;
        s = 0;

        std::cout<< "Dati_n\n";
        std::cin>> n;

        int i;
        i = 0;
        while(i < n){
                int nr;
                std::cin>> nr;
                s = s + nr;
                i = i + 1;
        }

        std::cout<< s;
        return 0;
}
