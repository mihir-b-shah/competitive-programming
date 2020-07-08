
public class KFilter {
    
    class Matrix {
        double[][] mat;
        
        Matrix(int m, int n) {
            mat = new double[m][n];
        }
        
        Matrix(Matrix sim, double c) {
            Matrix(sim.mat.length, sim.mat[0].length);
            for(int i = 0; i<sim.mat.length; ++i) {
                for(int j = 0; j<sim.mat[0].length; ++j) {
                    mat[i][j] = c;
                }
            }
        }
        
        Matrix(double[][] m){
            mat = m;
        }
        
        static Matrix identity(int s){
            Matrix m = new Matrix(s,s);
            for(int i = 0; i<s; ++i) {
                m.mat[i][i] = 1;
            }
            return m;
        }
        
        static Matrix zeros(int m, int n){
            return new Matrix(m,n);
        }
        
        static Matrix mult(Matrix a, Matrix b) {
            Matrix c = new Matrix(a.length, b[0].length);
            assert(a[0].length == b.length);
            for(int i = 0; i<a.length; ++i) {
                for(int j = 0; j<b[0].length; ++j) {
                    double accm = 0;
                    for(int k = 0; k<a[0].length; ++k) {
                        accm += a.mat[i][k]*b.mat[k][j];
                    }
                    c.mat[i][j] = accm;
                }
            }
            return c;
        }
        
        Matrix copy() {
            m = new double[mat.length][mat[0].length];
            for(int i = 0; i<mat.length; ++i) {
                System.arraycopy(mat[i], 0, m[i], 0, mat[0].length);
            }
            return m;
        }
        
        Matrix transpose(){
            m = new double[mat[0].length][mat.length];
            for(int i = 0; i<mat.length; ++i) {
                for(int j = 0; j<mat[0].length; ++j) {
                    m[j][i] = mat[i][j];
                }
            }
            return m;
        }
        
        Matrix invert() {
            double[][] aux = new double[mat.length][mat.length<<1];
            for(int i = 0; i<mat.length; ++i) {
                System.arraycopy(mat[i], 0, aux[i], 0, mat.length);
                aux[i][mat.length+i] = 1f;
            }
            
            // first find triangular matrix, 1s on diagonal, 0 on Left
            
            outer: for(int i = 0; i<aux.length; ++i) {
                for(int j = 0; j<i; ++j) {
                    int pos = i-1;
                    if(aux[i][j] != 0d) {
                        while(aux[pos][j] == 0) {
                            --pos;
                        }
                        if(pos == -1) {
                            System.err.println("Matrix not invertible.");
                            return null;
                        }
                        double mult = aux[i][j]/aux[pos][j];
                        for(int k = 0; k<aux[0].length; ++k) {
                            aux[i][k] -= mult*aux[pos][k];
                        }
                    }
                }
                
                if(aux[i][i] == 0) {
                    System.err.println("Matrix not invertible.");
                    return null;
                }
                
                if(aux[i][i] != 1d) {
                    double mult = 1/aux[i][i];
                    for(int c = 0; c<aux[i].length; ++c) {
                        aux[i][c] *= mult;
                    }
                }
            }
            
            for(int i = aux.length-2; i>=0; --i) {
                // map aux[i] <- aux[i] - k*aux[i+1]
                for(int j = i+1; j<aux.length; ++j) {
                    double mult = aux[i][j]/aux[j][j];
                    for(int k = 0; k<aux[i].length; ++k) {
                        aux[i][k] -= mult*aux[j][k];
                    }
                }
            }
            
            double[][] inv = new double[mat.length][mat.length];
            
            for(int i = 0; i<mat.length; ++i) {
                System.arraycopy(aux[i], mat.length, inv[i], 0, mat.length);
            }
            
            return new Matrix(inv);
        }
    }
    
    class KFilterState {
        Matrix H;
        Matrix T;
        Matrix Q;
        Matrix R;
        Matrix state;
        Matrix prob;
        Matrix pstate;
        Matrix pprob;

        KFilterState(Matrix H, Matrix T, Matrix Q, Matrix R, Matrix state, Matrix prob) {
            this.H = H;
            this.T = T;
            this.Q = Q;
            this.R = R;
            this.state = state;
            this.prob = prob;
            pstate = state.copy();
            pprob = prob.copy();
        }
        
        void iterate(Matrix obs) {
            kalmanGain = Matrix.mult(Matrix.mult(pprob, H.transpose()), Matrix.mult(Matrix.mult(H, pprob), H.transpose())
        }
    }

    public static void main(String[] args){
        
    }
}