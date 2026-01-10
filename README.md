# Ray Tracing in One Weekend

This is my implementation of the code for the class Ray Tracing in One Weekend located
at https://raytracing.github.io/books/RayTracingInOneWeekend.html

## Notes
* Initial version used immutable objects for the points and vectors, but this came with a
  high-cost due to excessive object allocation and garbage collection, so those objects were
  made mutable.
* Multi-threading was added to handling the anti-aliasing rays.
* After some profiling, the RNG was switched from SecureRandom to ThreadLocalRandom, which
  has much better performance, even for non-threaded cases.
* A lot of time was still being used creating points and vectors, so some additional methods
  were added to reduce the number of copies needed for calculations.

# Ray Tracing: The Next Week

Project continues with the 2nd book in the series,
https://raytracing.github.io/books/RayTracingInOneWeekend.html

## Notes
