#!/usr/bin/perl
use strict;
use warnings FATAL => 'all';

# After a BLAST search is performed against the PD database using the web_blast.pl script,
# this script is used to retrieve the ID of the highest scoring match and retrieve
# the corresponding PDB file from the PDB database.

# Modules required to parse BLAST search results and prepare the alignment file for modelling.
use Bio::SearchIO;
use Bio::SeqIO;
use File::Basename;
use File::Spec;

# Capture file to be parsed from @ARGV array.
my $bls = $ARGV[0];
# Capture the directory of the input file.
my $dir = dirname($bls);
# Cpature the fasta file to be converted to a PIR (.ali) file.
# Generate the object to contain the fasta file.
my $fasta = $ARGV[1];
my $fasta_file = Bio::SeqIO -> new(-file => "$fasta", -format => "fasta");
# Generate the file name for the PIR (.ali) file with the same name as the given fasta file.
$fasta =~ s/.fasta/.ali/;
my $ali = $fasta;
# Generate the object for the new PIR (.ali) file.
my $alifile = Bio::SeqIO -> new(-file => ">$ali", -format => "pir");
# Write to the PIR (.ali) file.
while (my $seq = $fasta_file -> next_seq()) {$alifile -> write_seq($seq);}

# Create the object to contain the blast result (.bls) file.
my $searchio = Bio::SearchIO -> new(
    -format => 'blast',
    -file => "$bls"
);
# Retrieve the protein ID
my $seqid = $searchio -> next_result -> next_hit -> name;
# Clip the chain identifier from the ID.
$seqid =~ s/_.?/.pdb/;

# Retrieve the appropriate PDB file from the PDB database.
system "wget https://files.rcsb.org/download/$seqid -P $dir";

# Edit the PIR (.ali) ready for Modeller.
my @lines = ();
open (FILE, "<$ali") or die "$!";
foreach (<FILE>) {
    push @lines, $_;
}
close FILE;
chomp($lines[1]);
$lines[1] = "sequence:$lines[1]::::::::\n";
open (FILE, ">$ali") or die "$!";
foreach (@lines) {
    print FILE $_;
}
close FILE;

# Move the PIR (.ali) file to the ProteinModel directory in the user home directory.
system "mv $ali $dir";

my $program = "model_prep.pl";
$program = File::Spec -> rel2abs($program);

system "perl $program $ali $seqid";