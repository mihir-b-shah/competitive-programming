
#include <iostream>
#include <unordered_map>

using namespace std;

long long toNumber(char* bits) {
    long long buffer = 0;
    while(*bits != '\0') {
        buffer <<= 1;
        buffer |= *bits-'0';
        ++bits;
    }
    return buffer;
}

long long computeAbsent(int M, long long index) {
    int height = 0;
    while(index > 0) {
        index = (index-1) >> 1;
        ++height;
    }
    return 1LL << M-height;
}

long long getValue(int key, int M, unordered_map<long long,long long>& mmap) {
    if(mmap.find(key) == mmap.end()) {
        return computeAbsent(M, key);
    } else {
        return mmap[key];
    }
}

int main() {
    int T;
    cin >> T;
    for(int i = 0; i<T; ++i) {
        int N,M;
        cin >> N >> M;
        const int constM = M;
        char buffer[constM+1];
        unordered_map<long long, long long> mmap;

        for(int j = 0; j<N; ++j) {
            cin >> buffer;
            long long val = (1LL<<M)-1+toNumber(buffer);
            // not found in map
            while(val > 0) {
                if(mmap.find(val) == mmap.end()) {
                    mmap[val] = computeAbsent(M, val);
                } 
                mmap[val] -= 1;
                val = (val - 1) >> 1;
            }
        }
        long long tgt = ((1LL<<M)-N-1)>>1;
        long long base = 0;
        long long left,right;
        long long follow = 0;
        long long restrFollow = 0;
        int ctr = 0;

        while(ctr < M) {
            ++ctr;
            left = 1+(base << 1);
            right = left+1;
            long long cache = getValue(left, M, mmap);
            if(tgt - restrFollow < cache) {
                // it lies in the left subtree
                base = left;
            } else {
                // it lies in the right subtre
                base = right;
                follow += 1LL << M-ctr;
                restrFollow += cache;
            }
        }
        while(follow > 0) {
            buffer[--ctr] = '0'+(follow&1);
            follow >>= 1;
        }
        while(ctr > 0) {
            buffer[--ctr] = '0';
        }
        cout << buffer << '\n';
    }
    cout.flush();
}