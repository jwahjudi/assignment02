# Running Time
The first requirement should take around O(n+m), where n is the number of lines and m is the number of names being added. 
The second requirement, which uses BFS algorithm, have a best case running time of O(1), which is when the the source and target played in the same movie, while worst case should be around O(v+e), which is when we have to go through each vertex and edges until a path is found. Code could be faster if we also run another BFS from the target too, but there is not much time for me to think it through.
