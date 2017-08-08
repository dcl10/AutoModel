#! /usr/bin/perl

package AliWriter;

use strict;
use warnings FATAL => 'all';

sub write_header {
	my $alifile = $_[0];
    my $header = $_[1];
    open (FILE, ">>", $alifile);
    if ($header) {
        # Give the header a special name to generate the model names.
        print FILE ">P1;$header\n";
    }
    else {
        # Specify the template sequence.
        print FILE ">P1;template\n";
    }
    close FILE;
}

sub write_instruction {
    my $alifile = $_[0];
    my $instruction = $_[1];
    open (FILE, ">>", $alifile);
    if ($instruction) {
        print FILE "structure:$instruction:FIRST:\@:LAST:\@:.:.:.:.\n";
    }
    else {
        print FILE "sequence:target:.:.:.:.:.:.:.:.\n";
    }
    close FILE;
}

sub write_sequence {
    my $alifile = $_[0];
    my $sequence = $_[1];
    open (FILE, ">>", $alifile);
    print FILE "$sequence*\n";
    close FILE;
}

1;
