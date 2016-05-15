# Generic ABNF Rules

```properties
; inherit from https://tools.ietf.org/html/rfc5234#appendix-B.1
; any visible character except double quote %x21 and whitespace %x20
UCHAR = %x21 / %x23-7E / UTF8-NON-ASCII 
; http://tools.ietf.org/html/rfc6531#section-3.3
UTF8-NON-ASCII  = %x80-FF / ; Latin-1 Supplement
                  %x100-17F / ; Latin Extended-A
                  %x370-3FF / ; Greek and Coptic
                  %x400-4FF / ; Cyrillic
                  %x500-52F / ; Cyrillic Supplement
                  %x4E00-9FFF ; CJK Unified Ideographs
NUMBER = FRACTIONAL-NUMBER / REAL-NUMBER / "NaN"           
FRACTIONAL-NUMBER = POSITIVE-INTEGER ["." 1*DECIMAL-DIGIT]                  
POSITIVE-INTEGER = NON-ZERO-DIGIT *DECIMAL-DIGIT
DECIMAL-DIGIT  = %x30-39  ; "0" to "9"
NON-ZERO-DIGIT  = %x31-39  ; "1" to "9"  
REAL-NUMBER = MANTISSA EXPONENT
MANTISSA   = (POSITIVE-INTEGER [ "." *DECIMAL-DIGIT ]) / ( "0." *("0") POSITIVE-INTEGER )
EXPONENT   = "E" ( "0" / ([ "-" ] POSITIVE-INTEGER))
```

