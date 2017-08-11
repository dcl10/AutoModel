#! /usr/bin/perl

use strict;
use warnings FATAL => 'all';
use File::Spec;

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

my $alimaker = "Additional/alimaker.py";
$alimaker = File::Spec -> rel2abs($alimaker);

system "python $alimaker $alifile $aligncode $pdbfile";

my $model = "Additional/model.py";
$model = File::Spec -> rel2abs($model);

system "python $model $alifile $aligncode $pdbfile";