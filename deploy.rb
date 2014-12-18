@imports = []
@content = []

Dir.glob("./src/nl/codecup/src/**/*").each do |file|
  if File.file? file
    puts file
    File.open(file, 'r').each do |line|
      if line.start_with? 'import'
        @imports << line
      elsif !line.start_with? 'package'
        @content << line
      end
    end
  end
end

concat  = @imports.uniq.sort.join("\n")
concat << @content.join("\n")
concat.gsub!(/\/\*.+?\*\//m, '')
concat.gsub!(/^\s*[\r\n]/m, '')

out_file = File.new("caiaio.java", "w")
out_file.puts concat
out_file.close
