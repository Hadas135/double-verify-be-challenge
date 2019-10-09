# DoubleVerify back-end challenge

This project executes long numbers arithmetic (addition, multiplication) http web service, by implementing two Http APIs: one gets 2 long numbers and adds them, and one gets 2 long numbers and multiply them,
when long number is a number that cannot be held in a primitive variable of any kind thus it is held in an int array at the form of each digit is a cell in the array.  
For example: 123456789 → [1,2,3,4,5,6,7,8,9]

### The programming language used: 
Java
### Algorithm description:
Multiplying the numbers is used by [A fast Fourier transform (FFT) algorithm](https://en.wikipedia.org/wiki/Fast_Fourier_transform), according to the next steps:

1. Each one of the numbers transforms into long number, which is the polynomial coefficient array.
2. The correct amount of zeros is added to each array (the size of the array has to be an exponent of 2, for Fourier transform).
3. Algorithm FFT activities on each long number, using the unit-roots.
4. Point-to-point multiplication is performed on the array obtained from Fourier transform.
5. For the coefficient array of the multiplication, the FFT-1 algorithm is activated.
6. The array that turns out transform back to long number, including the removal of the unnecessaries zeros.
7. In the end, a transform is executed back from long number into a number.

### Running instructions:
To sum, write in your browser:  
`http://127.0.0.1/add?<first_number>,<second_number>`  
For example:  
`http://127.0.0.1/add?111,456`  

To multiply, write in your browser:  
`http://127.0.0.1/mult?<first_number>,<second_number>`  
For example:  
`http://127.0.0.1/mult?999,999`

#### Notes:
* Besides implementing the algorithm itself, the extra challenge was creating the http web service properly.
* If I had more time, I would have performed extra checks on the input and taking care of the edge cases.
