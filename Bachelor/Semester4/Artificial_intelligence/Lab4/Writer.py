def writeSolution(filename, chromosome):
    nrNodes = len(chromosome.permutation.values())
    words = filename.split(".")
    solutionFilename = ".".join(words[:-1]) + "_mySolution." + words[-1]
    f = open(solutionFilename, "w")
    f.writelines(str(nrNodes)+"\n")
    f.writelines(str(list(chromosome.permutation.values()))+"\n")
    f.writelines(str(chromosome.fitness)+"\n")
