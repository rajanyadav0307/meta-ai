SUMMARY = "Llama.cpp library for AI inference"
DESCRIPTION = "Optimized llama.cpp library for ARM (RPi4) with NEON/vectorization"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=<replace_with_actual_md5>"

SRC_URI = "git://github.com/ggerganov/llama.cpp.git;branch=master;protocol=https"
# SRCREV = "<commit_hash>"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

# Enforce Release build and shared library
EXTRA_OECMAKE = "\
    -DCMAKE_BUILD_TYPE=Release \
    -DBUILD_SHARED_LIBS=ON \
    -DLLAMA_BUILD_COMMON=ON \
    -DLLAMA_BUILD_TOOLS=OFF \
    -DLLAMA_BUILD_EXAMPLES=OFF \
    -DLLAMA_USE_SYSTEM_GGML=OFF \
    -DCMAKE_C_FLAGS='-O3 -mcpu=cortex-a72 -mfpu=neon -ftree-vectorize' \
    -DCMAKE_CXX_FLAGS='-O3 -mcpu=cortex-a72 -mfpu=neon -ftree-vectorize' \
    -DLLAMA_SANITIZE_ADDRESS=OFF \
    -DLLAMA_SANITIZE_THREAD=OFF \
    -DLLAMA_SANITIZE_UNDEFINED=OFF"

# Explicitly list headers to install
do_install_append() {
    install -d ${D}${includedir}/llama
    install -m 0644 ${S}/include/llama.h ${D}${includedir}/llama/
    install -m 0644 ${S}/include/llama-cpp.h ${D}${includedir}/llama/
}

FILES_${PN} = "${libdir}/libllama.so ${includedir}/llama"
