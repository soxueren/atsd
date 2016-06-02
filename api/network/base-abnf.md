# Base ABNF Rules

## Text Rules

```properties
  ; prefix:field
  ; all printable characters, optionally enclosed in double-quotes
  ; inner double-quote must be escaped with another double quote.
  ; e:my=entity
  ; StringEscapeUtils.escapeCsv
FIELD = NAME_QUOTED / 1*(CHAR_SAFE / EQUAL)

  ; prefix:field_name=field_value
  ; same as FIELD except EQUAL sign must be enclosed
  ; t:"tag=name"=tag-value
FIELD_NAME = NAME_QUOTED / 1*CHAR_SAFE

  ; StringEscapeUtils.escapeCsv
FIELD_VALUE = TEXT_QUOTED / 1*(CHAR_SAFE / EQUAL)

NAME_QUOTED = DQUOTE 1*(CHAR_SAFE / EQUAL / DQUOTE DQUOTE) DQUOTE

TEXT_QUOTED = DQUOTE 1*(CHAR_SAFE / EQUAL / DQUOTE DQUOTE / SPACE / CR / LF / TB) DQUOTE

MSP = 1*SP    ; multiple spaces
SPACE = %x20  ; space
DQUOTE = %x22 ; double-quote
EQUAL = %x3D  ; equal sign
CR = %x0D     ; carriage return
LF = %x0A     ; line feed
TB = %x09     ; tab

  ; printable character with double-quote escaped with another double-quote
CHAR_ESCAPED = CHAR_SAFE / DQUOTE DQUOTE / EQUAL
  ; printable character except double-quote and equal sign
CHAR_SAFE = %x21 / %x23-3C / %x3E-7E / UNICODE
  ; Unicode character
  ; http://tools.ietf.org/html/rfc6531#section-3.3
UNICODE = %x80-FF / ; Latin-1 Supplement
          %x100-17F / ; Latin Extended-A
          %x370-3FF / ; Greek and Coptic
          %x400-4FF / ; Cyrillic
          %x500-52F / ; Cyrillic Supplement
          %x4E00-9FFF ; CJK Unified Ideographs
```

## Date Rules
```properties
  ; ISO date defined in RFC-3339 Appendix-A. 
  ; Format yyyy-MM-dd'T'HH:mm:ss.SSSX
  ; https://tools.ietf.org/html/rfc3339#appendix-A
ISO_DATE = date-time 
```

## Number Rules

```properties
NUMBER = ["-"] (FRACTIONAL_NUMBER / REAL_NUMBER) / "NaN"           
FRACTIONAL_NUMBER = ("0" / POSITIVE_INTEGER) ["." 1*DIGIT]                  
POSITIVE_INTEGER = %x31-39 *DIGIT
  ; "0" to "9"
DIGIT = %x30-39
  ; https://tools.ietf.org/html/rfc3642  4.  ASN.1 Built-in Types
REAL_NUMBER = MANTISSA EXPONENT
MANTISSA   = (POSITIVE_INTEGER [ "." *DIGIT ]) / ( "0." *"0" POSITIVE_INTEGER)
EXPONENT   = ["E"/"e"] ( "0" / ([ "-" ] POSITIVE_INTEGER))
```


