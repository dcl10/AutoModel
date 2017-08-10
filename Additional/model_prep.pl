#! /usr/bin/perl

use strict;
use warnings FATAL => 'all';

my $alifile = $ARGV[0];
my $pdbfile = $ARGV[1];

my @file = ();
open (FILE, "<$alifile") or die "$!";
foreach (<FILE>) {
    chomp;
    push @file, $_;
}
my $aligncode = $file[0];
$aligncode =~ s/\>P1;//;
if ($aligncode =~ /\|/g) {$aligncode =~ s/\|/\\|/g;}

system "python alimaker.py $alifile $aligncode $pdbfile";

system "python model.py $alifile $aligncode $pdbfile";