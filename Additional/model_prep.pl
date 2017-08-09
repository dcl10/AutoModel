#! /usr/bin/perl

use strict;
use warnings FATAL => 'all';

my $alifile = $ARGV[0];
my $pdbfile = $ARGV[1];

# Harvest the aligncode from the .ali file

system "python alimaker.py $alifile $pdbfile";

