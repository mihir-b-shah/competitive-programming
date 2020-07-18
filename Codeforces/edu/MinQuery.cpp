
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
		int n;
	public:
		SparseTable(int N, int* data){
		}
		~SparseTable(){
		}
		int query(int s, int e){
			return 0;
		}
};

int main(){
}
