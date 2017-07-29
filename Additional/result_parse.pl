#!/usr/bin/perl
use strict;
use warnings FATAL => 'all';

# After a BLAST search is performed against the PD database using the web_blast.pl script,
# this script is used to retrieve the ID of the highest scoring match and retrieve
# the corresponding PDB file from the PDB database.

# Module required to parse BLAST search results.
use Bio::SearchIO;

# Capture file to be parsed from @ARGV array.
my @input = @ARGV;
# Variable to hold the ID of the top hit.
my $seqid;

# For each file passed in @ARGV, parse the results to get the ID of the highest scoring
# protein structure
foreach (@input) {
    my $searchio = Bio::SearchIO -> new(
        -format => 'blast',
        -file => "$_"
    );
    # Retrieve the ID of the top hit.
    while (my $result = $searchio -> next_result) {
        if ($result -> num_hits == 0) {print "No results found.\n";}
        else {
            while (my $hit = $result -> next_hit) {
                $seqid = $hit -> name;
                last;
            }
        }
    }
    # Clip the chain identifier from the ID.
    $seqid =~ s/_.?/.pdb/;

    # Retrieve the appropriate PDB file from the PDB database.
    system "wget https://files.rcsb.org/download/$seqid";
}





