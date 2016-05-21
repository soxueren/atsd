# Generic ABNF Rules

```properties
  ; visible characters, double-quotes escaped with backslash
NAME = 1*NCHAR / DQUOTE 1*(NCHAR / "\" DQUOTE) DQUOTE  
  ; visible characters and spaces. double-quotes escaped with backslash
VALUE = 1*NCHAR / DQUOTE 1*(NCHAR / SP / "\" DQUOTE) DQUOTE
  ; space
SP = %x20 
  ; double-quote
DQUOTE = %x21
  ; visible character except double-quote
NCHAR = %x21 / %x23-7E / UNICODE
  ; visible character
VCHAR = %x21-7E / UNICODE
  ; Unicode character
  ; http://tools.ietf.org/html/rfc6531#section-3.3
UNICODE = %x80-FF / ; Latin-1 Supplement
          %x100-17F / ; Latin Extended-A
          %x370-3FF / ; Greek and Coptic
          %x400-4FF / ; Cyrillic
          %x500-52F / ; Cyrillic Supplement
          %x4E00-9FFF ; CJK Unified Ideographs
  ; ISO date defined in RFC-3339 Appendix-A. 
  ; Format yyyy-MM-dd'T'HH:mm:ss.SSSZ
  ; https://tools.ietf.org/html/rfc3339#appendix-A
ISO-DATE = date-time 
NUMBER = ["-"] (FRACTIONAL-NUMBER / REAL-NUMBER) / "NaN"           
FRACTIONAL-NUMBER = ("0" / POSITIVE-INTEGER) ["." 1*DIGIT]                  
POSITIVE-INTEGER = %x31-39 *DIGIT
  ; "0" to "9"
DIGIT = %x30-39
  ; https://tools.ietf.org/html/rfc3642  4.  ASN.1 Built-in Types
REAL-NUMBER = MANTISSA EXPONENT
MANTISSA   = (POSITIVE-INTEGER [ "." *DIGIT ]) / ( "0." *"0" POSITIVE-INTEGER)
EXPONENT   = ["E"/"e"] ( "0" / ([ "-" ] POSITIVE-INTEGER))
```

