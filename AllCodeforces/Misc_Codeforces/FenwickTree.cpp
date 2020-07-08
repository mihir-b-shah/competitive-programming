
#include <cstddef>

template<size_t N>
class FenwickTree {
    private:
        static constexpr int MAX_STACK_ALLOC = 1000;
        static constexpr int BASE_BUFFER_LEN = 1;
        static constexpr int bufferLen(){
            return N > MAX_STACK_ALLOC ? BASE_BUFFER_LEN : N;
        }
        union {
            int* ptr;
            int buf[bufferLen()];
        }
    public:
        FenwickTree(int* data){
            // no runtime penalty!
            int* tree = bufferLen() == BASE_BUFFER_LEN ? buf : ptr;
            
        }
        ~FenwickTree(){
        }
        
}