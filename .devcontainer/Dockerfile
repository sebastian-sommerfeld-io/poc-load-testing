FROM mcr.microsoft.com/devcontainers/java:21-bookworm
LABEL maintainer="sebastian@sommerfeld.io"

ARG USER=vscode

RUN apt-get update \
    && apt-get install -y --no-install-recommends python3-pip=23.0.1+dfsg-1 \
    && apt-get install -y --no-install-recommends pre-commit=3.0.4-1 \
    && apt-get install -y --no-install-recommends maven=3.8.7-1 \
    && apt-get install -y --no-install-recommends kotlin=1.3.31+ds1-1 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN curl -sSLO https://github.com/pinterest/ktlint/releases/download/0.48.0/ktlint \
    && chmod a+x ktlint \
    && mv ktlint /usr/local/bin/

COPY configs/etc/login.defs /etc/login.defs
RUN chmod og-r /etc/shadow \
    && chmod 0444 /etc/login.defs

USER $USER

SHELL ["/bin/bash", "-o", "pipefail", "-c"]
RUN echo "alias ls='ls -a --color=auto'" >> /home/$USER/.bashrc \
    && echo "alias ll='ls -alF --color=auto'" >> /home/$USER/.bashrc
