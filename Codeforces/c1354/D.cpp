
#pragma GCC optimize("Ofast")

#include <iostream>

class Tree {
    private:
        int nextTree(int n){
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
            int left;

            Info(){}
        };
        Info* tree;
        int size;
    public:
        Tree(int N){
            size = nextTree(N);
            tree = new Info[size];
            for(int i = 0; i<size; ++i){
                tree[i].num = i+1;
                tree[i].ct = 0;
                tree[i].left = 0;
            }
        }
        ~Tree(){
            delete tree;
        }
        void insert(int v){
			int start = (size+1) >> 1;
			int jmp = (size+1) >> 2;
			
			while(jmp >= 0){
				if(v > start){
					start += jmp;
				} else if(v < start){
					++tree[start-1].left;
					start -= jmp;
				} else {
					++tree[start-1].ct;	
					break;
				}
				jmp >>= 1;
			}	
        }
        void erase(int k) {
			k -= 1;
			int start = (size+1) >> 1;
			int jmp = (size+1) >> 2;
			
			while(jmp >= 0){
				if(k >= tree[start-1].left+tree[start-1].ct){
					k -= tree[start-1].left + tree[start-1].ct;
					start += jmp;	
				} else if(k < tree[start-1].left){
					--tree[start-1].left;
					start -= jmp;
				} else {
					--tree[start-1].ct;
					break;
				}
				jmp >>= 1;
			}	
        }
		void print(std::ostream& out){
			for(int i = 0; i<size; ++i){
				out << '(' << tree[i].num<< ' ' << tree[i].ct<< ' ' << tree[i].left << ')' << ' ';   
			}
			out << '\n';
		}
		int find(){
			for(int i = 0; i<size; ++i){
				if(tree[i].ct > 0){
					return tree[i].num;
				}
			}	
			return 0; 
		}
};

int main(){
    int N,Q;
	std::ios_base::sync_with_stdio(0); 
	std::cin.tie(0);	

	std::cin >> N >> Q;
    
    Tree tree(N);   
    int v;
	for(int i = 0; i<N; ++i){
		std::cin >> v;
		tree.insert(v);
	}
	for(int i = 0; i<Q; ++i){
		std::cin >> v;
		if(v > 0){
			tree.insert(v);
		} else {
			tree.erase(-v);
		}
	}

	std::cout << tree.find() << '\n';

	return 0;
}
