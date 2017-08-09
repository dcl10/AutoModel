#! /usr/bin/python3.5

from modeller import *
from modeller.automodel import *
from sys import argv

aln = argv[1]
#novel = str(argv[2]).replace('.fasta', '')

env = environ()
a = automodel(env, alnfile=aln,
              knowns='3G33', sequence='sp|P11802|CDK4_HUMAN',
              assess_methods=(assess.DOPE, assess.GA341))
a.starting_model = 1
a.ending_model = 5
a.make()
