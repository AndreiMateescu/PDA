#include<iostream>
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
using namespace std;

#define N 5
#define INF 99999

__global__ void Floyd(float* graph, int k)
{
    int i = blockIdx.x + threadIdx.x;
	int j = blockIdx.y + threadIdx.y;
	
    if(graph[i][k] + graph[k][j] < graph[i][j])
		graph[i][j] = graph[i][k] + graph[k][j];
}
            
int main()
{
    size_t size = N * N * sizeof(int);

	int h_graph[N][N] = {
		0,3,9,8,3,
		5,0,1,4,2,
		6,6,0,4,5,
		2,9,2,0,7,
		7,9,3,2,0,
	};
	
    int* d_graph;
    cudaMalloc(&d_graph, size);
	
    cudaMemcpy(d_graph, h_graph, size, cudaMemcpyHostToDevice);

    dim3 threadsPerBlock(N, N);
	int numBlocks = 1;

	for(int k = 0; k < N; k++)
	{
		Floyd<<<numBlocks, threadsPerBlock>>>(d_graph, k);

	}
		
    cudaMemcpy(h_graph, d_graph, size, cudaMemcpyDeviceToHost);

    cudaFree(d_graph);
            
    for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			if (h_graph[i][j] == INF)
				cout << "INF ";
			else
				cout << h_graph[i][j] << " ";
		}
		cout << endl;
	}
}