import java.util.Random;
import mpi.*;

public class Application {

    public static void main(String args[]) throws Exception {
        Random randomNumbers = new Random();
        MPI.Init(args);
        int my_rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        MPI.COMM_WORLD.Barrier();
        int[] n = new int[100];
        int[] my_sum = new int[1];
        int blockNum, start, end;
        int finalSum = 0;

        if (my_rank == 0)   {
            System.out.println("");

            for (int i = 0; i < 20; i++)    {
                n[i] = 1 + randomNumbers.nextInt(50);
                System.out.printf("%d| ", n[i]);
            }
            System.out.printf("\n");
        }

        MPI.COMM_WORLD.Bcast(n, 0, 20, MPI.INT, 0);

        my_sum[0] = 0;
        blockNum = 20/size;
        start = my_rank * blockNum;
        end = start + blockNum;

        for (int i = start; i < end; i++)   {
            my_sum[0] = my_sum[0] + n[i];
        }
        System.out.println("Result from processor " +my_rank+ " = " +my_sum[0]);

        if (my_rank == 0){
            finalSum = my_sum[0];
            for (int i = 1; i < size; i++)  {
                MPI.COMM_WORLD.Recv(my_sum, 0, 1, MPI.INT, MPI.ANY_SOURCE,99);
                finalSum = finalSum + my_sum[0];
            }
        }
        else {
            MPI.COMM_WORLD.Send(my_sum, 0, 1, MPI.INT, 0, 99);
        }

        if (my_rank == 0)   {
            System.out.println("Result = " + finalSum);
        }

        MPI.Finalize();
    }
}