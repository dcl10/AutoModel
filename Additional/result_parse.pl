#!/usr/bin/perl
use strict;
use warnings FATAL => 'all';

# After a BLAST search is performed against the PD database using the web_blast.pl script,
# this script is used to retrieve the ID of the highest scoring match and retrieve
# the corresponding PDB file from the PDB database.

# Modules required to parse BLAST search results and prepare the alignment file for modelling.
use Bio::SearchIO;
use Bio::SeqIO;
use AliWriter;

# Capture file to be parsed from @ARGV array.
my $input = $ARGV[0];
# Capture the file of the novel sequence.
my $seqio_obj = Bio::SeqIO -> new(-file => $ARGV[1]);
my $novel_seq = $seqio_obj -> next_seq -> seq;
# Variable to hold the ID of the top hit.
my $seqid;
# Variable to hold the aligned sequence.
my $alnseq;
# Variable to hold .ali file name.
my $alifile;

#
my $searchio = Bio::SearchIO -> new(
    -format => 'blast',
    -file => "$input"
);
# Retrieve the ID and homologous sequence
while (my $result = $searchio -> next_result) {
    if ($result -> num_hits == 0) {print "No results found.\n";}
    else {
        while (my $hit = $result -> next_hit) {
            $seqid = $hit -> name;
            $alnseq = $hit -> next_hsp -> homology_string;
            last;
        }
    }
}
# Clip the chain identifier from the ID.
$seqid =~ s/_.?/.pdb/;

# Retrieve the appropriate PDB file from the PDB database.
system "wget https://files.rcsb.org/download/$seqid";

# Make .ali file name.
$alifile = $seqid;
$alifile =~ s/.pdb/.ali/;

# Write the template portion of the .ali file.
AliWriter::write_header($alifile);
AliWriter::write_instruction($alifile, $seqid);
AliWriter::write_sequence($alifile, $alnseq);

# Get the name of the novel sequence.
my $new_prot_name = $input;
$new_prot_name =~ s/.bls//;

# Write the target portion of the .ali file
AliWriter::write_header($alifile, $new_prot_name);
AliWriter::write_instruction($alifile);
AliWriter::write_sequence($alifile, $novel_seq);