#include<mpi.h>
#include<iostream>
#include<time.h>
#include<Windows.h>
#define N 5
#define INF 99999
using namespace std;

int graph[N][N] = { 
		0,3,9,8,3,
		5,0,1,4,2,
		6,6,0,4,5,
		2,9,2,0,7,
		7,9,3,2,0
		};

int main(int argc, char** argv)
{
	int size, procid, rc;

	rc = MPI_Init(&argc, &argv);
	if (rc != MPI_SUCCESS)
	{
		cout << "Error starting MPI program. Terminating.\n";
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &procid);


	for (int k = 0; k < N; k++)
	{
		for (int j = 0; j < N; j++)
		{
			if(graph[procid][k] + graph[k][j] < graph[procid][j])
				graph[procid][j] = graph[procid][k] + graph[k][j];
		}

		MPI_Allgather(graph[procid], N, MPI_INT, graph, N, MPI_INT, MPI_COMM_WORLD);
		cout << "proc " << procid << " sent broadcast on k = " << k<<endl;

		cout << "proc " << procid << " arrived at barrier on k = " << k << endl;
		MPI_Barrier(MPI_COMM_WORLD);
		cout << "proc " << procid << " past barrier on k = " << k << endl;
	}

	if(procid == 0)
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++)
			{
				if (graph[i][j] == INF)
					cout << "INF ";
				else
					cout << graph[i][j] << " ";
			}
			cout << endl;
		}

	MPI_Finalize();
	return 0;
}
