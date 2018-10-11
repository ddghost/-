package main
import (
	"fmt"
	"os"
	"bufio"
	"io"
	"os/exec"
	"time"
	goflag "flag"
	flag "github.com/spf13/pflag"
)

var(
	start_page int
	end_page int
	page_type bool
	page_len int
	help bool
	inputFileName string
	print_dest string
)


func main() {
	
	flag.CommandLine.AddGoFlagSet(goflag.CommandLine)
	bindArg()
	flag.Parse()
	//fmt.Println(flag.Args() , flag.NArg( ) )
	run()
	if help{
		flag.Usage()
	}
}

func bindArg(){
	flag.IntVarP( &start_page , "s" , "s" , -1, "start page")
	flag.IntVarP( &end_page , "e" , "e" , -1, "end page")
	flag.BoolVarP(&page_type ,"f", "f" ,false , "page type")
	flag.IntVarP(&page_len , "l", "l" , 72, "page length")
	flag.BoolVarP(&help , "h", "h", false, "show help")
	flag.StringVarP(&print_dest , "d", "d" , "", "print detination")
}

func outputFile(inputFile *os.File , fout io.WriteCloser	){
	nowPage := 1
	nowRow := 0
	var err error
	var line string
	defer inputFile.Close()
	rd := bufio.NewReader(inputFile)

	for {
		if page_type {
			line, err = rd.ReadString('\f') 
		}else{
			line, err = rd.ReadString('\n') 
		}
		if err != nil || io.EOF == err {
			break
		}
		if nowPage > end_page{
			return 
		}else if nowPage >= start_page{
			fout.Write([]byte(line))
		}
		
		if page_type {
			nowPage++
		}else{
			nowRow ++
			nowPage += nowRow / page_len
			nowRow %= page_len
		}
		
	} 
}

func checkArgValid() bool{
	switch  {
	case start_page <= 0 || end_page <= 0:	
		fmt.Fprintf(os.Stderr ,"Error! you must set page num positive\n")
	case start_page > end_page:
		fmt.Fprintf(os.Stderr ,"Error! start page > end page\n")
	case page_type == true && page_len != 72:
		fmt.Fprintf(os.Stderr ,"page type conflict -f and -l can not be used together\n")
	default: return true
	} 
	return false
}

func run(){
	var inputFile *os.File
	var err error
	if flag.NArg() < 1{
		inputFile = os.Stdin
	}else{
		inputFileName = flag.Args()[0]
		inputFile, err = os.Open(inputFileName)
		if err != nil{
			fmt.Fprintln(os.Stderr, err)
		}
	}
	
	if checkArgValid() == true{
		var cmd *exec.Cmd
		var fout io.WriteCloser
		if print_dest == ""{
			fout = os.Stdout
			outputFile(inputFile , fout)
		}else{
			cmd = exec.Command("lp" , "-d" , print_dest)
			fout, err = cmd.StdinPipe()
			if err != nil {
				fmt.Fprintln(os.Stderr, err)
				os.Exit(1) 
			}
			outputFile(inputFile , fout)
			fout.Close()
			cmd.Stdout = os.Stdout
			cmd.Stderr = os.Stderr
			err = cmd.Start()
			if err != nil {
				fmt.Fprintln(os.Stderr, err)
				os.Exit(1) 
			}
			timer := time.AfterFunc(3*time.Second, func() {
				cmd.Process.Kill()
			})
			err = cmd.Wait()
			timer.Stop()
		}
	}
		
}
