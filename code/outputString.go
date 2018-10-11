package main

import (
    "fmt"
    "bufio"
    "os"
    "io"
)

func main() {
    reader := bufio.NewReader(os.Stdin)
    for true{
        input , err := reader.ReadString('\n')
        if err == io.EOF{
            break
        }
        fmt.Printf("%s" , input)
    }
}
