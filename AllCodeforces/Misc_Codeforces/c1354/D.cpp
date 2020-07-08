
#include <iostream>

class Tree {
    private:
        static constexpr int nextTree(int n){
            n = n | (n >> 1);
            n = n | (n >> 2);
            n = n | (n >> 4);
            n = n | (n >> 8);
            n = n | (n >> 16);
            return n;
        }
        struct Info {
            int num;
            int ct;
            int size;
            
            Info(){}
        };
        Info* tree;
        int N;
        int size;
    public:
        Tree(int N){
            this->N = N;
            size = nextTree(N);
            tree = new Info[size];
            for(int i = 0; i<size; ++i){
                tree[i].num = i+1;
                tree[i].ct = 0;
                tree[i].size = 0;
            }
        }
        ~Tree(){
            delete tree;
        }
        void insert(int v){
            int l = 1;
            int u = size;
            int med; 
            int jmp = (l+u)>>1;
            
            while(l <= u){
                med = (l+u)>>1;
                jmp >>= 1;
                
                if(v > tree[med].num){
                    l = med+1;
                } else if(v < tree[med].num){
                    u = med-1;
                } else {
                    // equal
                }
            }
        }
        void erase(int k){
        }
}

int main(){
    int N,Q;
    std::cin >> N >> Q;
    
    Tree tree(N);
    
}