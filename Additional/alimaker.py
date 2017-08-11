#! /usr/bin/python3.5

from modeller import *
from sys import argv

alifile = argv[1]
aligncode = argv[2]
pdbfile = argv[3]

env = environ()
aln = alignment(env)
mdl = model(env, file=pdbfile, model_segment=('FIRST:@','LAST:@'))
aln.append_model(mdl, align_codes=pdbfile, atom_files=pdbfile)
aln.append(file=alifile, align_codes=aligncode)
aln.align2d()
aln.write(file=alifile, alignment_format='PIR')
