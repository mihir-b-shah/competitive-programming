
#include <stdio.h>
#include <string.h>

#define INF 0x7f7f7f7f

int main() {
    FILE* infile = fopen("frameup.in", "r");
    int H,W;
    fscanf(infile, "%d %d", &H, &W);
    char grid[H*W];
    char maxChar = 0;
    for(int i = 0; i<H; ++i) {
        fscanf(infile, "%s\r\n", grid+i*W);
        for(int j = 0; j<W; ++j) {
            maxChar = grid[i*W+j] > maxChar ? grid[i*W+j] : maxChar;
        }
    }
    fclose(infile);
    
    const int rectLen = maxChar-'A'+1 << 2;
    int rect[rectLen];
    for(int i = 0; i<rectLen; ++i) {
        rect[i] = (i&1) == 0 ? INF : 0;
    }

    int baseIndex;
    for(int i = 0; i<H; ++i) {
        for(int j = 0; j<W; ++j) {
            baseIndex = grid[i*W+j]-'A' << 2;
            if(i < rect[baseIndex]) {
                rect[baseIndex] = i;
            }
            if(i > rect[baseIndex+1]) {
                rect[baseIndex+1] = i;
            }
            if(j < rect[baseIndex+2]) {
                rect[baseIndex+2] = j;
            }
            if(j > rect[baseIndex+3]) {
                rect[baseIndex+3] = j;
            }
        }
    }

    for(int i = 0; i<rectLen; ++i) {
        printf("%d ", rect[i]);
    }
    printf("\n");

    const int greaterLen = maxChar-'A'+1;
    int greater[greaterLen][greaterLen];
    memset(greater, 0, greaterLen*greaterLen*sizeof(int));

    char ch;
    for(int i = 0; i<H; ++i) {
        for(int j = 0; j<W; ++j) {
            ch = grid[i*W+j];
            if(ch == '.') continue;
            for(int k = 0; k<rectLen; k+=4) {
                if((i == rect[k] || i == rect[k+1]) && j >= rect[k+2] && j <= rect[k+3]
                || (j == rect[k+2] || j == rect[k+3]) && i >= rect[k] && i <= rect[k+1]) {
                    greater[ch-'A'][k >> 2] = 1;
                    greater[k >> 2][ch-'A'] = -1;
                }
            }
        }
    }

    for(int i = 0; i<greaterLen; ++i) {
        for(int j = 0; j<greaterLen; ++j) {
            printf("%3d ", greater[i][j]);
        }
        printf("\n");
    }

    int stack[greaterLen+2]; // for safety
    int mask;

    int stackPtr = 0;
    int src,dest;

    for(int i = 0; i<greaterLen; ++i) {
        for(int j = 0; j<greaterLen; ++j) {
            if(greater[i][j] == 0) {
                // do a dfs
                src = i; dest = j;
                mask = 0;

            
            }
        }
    }

}