#!/usr/bin/sh
# install generates the executable file 'fib'
cat <<'EOF' >fib
	#!/usr/bin/sh
	docker run --interactive \
	  --rm \
	  -p 8080:8080 \
		quarkus/fib
EOF