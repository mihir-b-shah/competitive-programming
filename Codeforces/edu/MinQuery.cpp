
#include <iostream>
#include <memory>

using namespace std;

class SegTree {
	private:
		int _min = 0x7f7f7f7f;
		int* lbound;
		int* rbound;
		unique_ptr<SegTree> left;
		unique_ptr<SegTree> right;
		static inline int min(int a, int b){
			return a<b?a:b;
		}
	public:
		SegTree(int* front, int* back){
			lbound = front;
			rbound = back;
			if(front == back){
				_min = *front;
				return;
			}
			int* med = front+(back-front)/2;
			int flags = 0;
			if(front <= med) {
				left = make_unique<SegTree>(front, med);
				flags |= 0b01;
			}
			if(med+1 <= back) {
				right = make_unique<SegTree>(med+1, back);
				flags |= 0b10;
			}
			switch(flags){
				case 0b00:
					break;
				case 0b01:
					_min = left->_min;
					break;
				case 0b10:
					_min = right->_min;
					break;
				case 0b11:
					_min = min(left->_min, right->_min); 
					break;
			}
		}
		~SegTree(){
		}
		// inclusive, returns 
		int query(int* front, int* back){
			if(front <= lbound && back >= rbound){
				return _min;
			}
			int* med = lbound + (rbound-lbound)/2;
			// if [front,back] is spanned by [lbound, rbound]
			int res = 0x7f7f7f7f;
			if(front <= med){
				// check in left
				res = min(res, left->query(front, med));
			}

			if(back >= med+1){
				// check in right
				res = min(res, right->query(med+1, back));
			}
			return res;
		}
};

/**
 * Notes on the Sparse Table technique
 * 
 *
 *
 *
 *
 *
 */
class SparseTable {
	private:
		int* table;
		int N;
		static int ilog2(int n){
			// not super efficient...
			int ctr = 0;
			while(n > 0){
				++ctr;
				n >>= 1;
			}
			return ctr-1;
		}
		static inline int min(int a, int b){
			return a<b?a:b;
		}
		static inline int argmin(int i1, int i2, int* map){
			return map[i1] < map[i2] ? i1 : i2;
		}
		static constexpr int INTMAX = 0x7fffffff;
	public:
		SparseTable(int N, int* data){
			this->N = N;
			int n = N+ilog2(N)*N;
			table = new int[n];

			for(int i = 0; i<N; ++i){
				table[i] = i;
			}
			int jmp = 1;
			for(int i = N; i<n; i+=N){
				for(int j = 0; j<N-jmp; ++j){
					table[i+j] = argmin(table[i-N+j], 
									table[i-N+j+jmp], data);
				}
				for(int j = N-jmp; j<N; ++j){
					table[i+j] = -1;
				}
				jmp *= 2;
			}
			for(int i = 0; i<ilog2(N)+1; ++i){
				for(int j = 0; j<N; ++j){
					cout << table[i*N+j] << ' ';
				} cout << '\n';
			}
		}
		~SparseTable(){
			delete table;
		}
		// precomputing the logs gives O(1) but its kind of useless.
		int query(int s, int e){
			int log = ilog2(e-s+1);
			return min(table[s+N*log], table[(e - (1 << log) + 1)+N*log]);
		}
};

int main(){
	int arr[7] = {1,3,4,11,6,2,9};
	SparseTable st(7, arr);
	cout << st.query(4,6) << endl;
	return 0;
}
