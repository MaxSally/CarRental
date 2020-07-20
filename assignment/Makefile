ASSIGNMENTS		= $(wildcard *.md)
ASSIGNMENT_HTML	= $(ASSIGNMENTS:%.md=%.html)
WORKSHEETS		= $(wildcard *.tex)
WORKSHEET_PDF	= $(WORKSHEETS:%.tex=%.pdf)

all: $(ASSIGNMENT_HTML) $(WORKSHEET_PDF)

%.html: %.md
	pandoc -o $@ $<
	sed "s/’/'/g" $@ | sed "s/‘/'/g" | sed 's/”/"/g' | sed 's/“/"/g' > temp
	mv temp $@

%.pdf: %.tex
	pdflatex $<
