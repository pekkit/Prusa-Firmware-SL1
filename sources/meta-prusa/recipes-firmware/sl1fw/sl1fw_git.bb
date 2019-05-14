SUMMARY = "sl1fw - python firmware part running on a64 board" 

LICENSE = "CLOSED"

SRC_URI = "git://git@gitlab.webdev.prusa3d.com:22443/hw/a64/SLA_fw.git;protocol=ssh;branch=master"
SRCREV_pn-${PN} = "0b8c3cb1fa055ef2b382645e1d7359a0af046142"

PACKAGES = "${PN}"


RDEPENDS_${PN} += "python rsync bash nginx python-websocket-server python-pygame python-pyserial python-pyroute2 python-numpy python-six python-numpy python-jinja2 python-gpio avahi-daemon avahi-restarter python-lazy-import api-keygen udev-usbmount web-config avrdude python-pydbus python-misc python-pygobject python-systemd socat python-bitstring fbset python-logging python-paho-mqtt python-toml"

FILES_${PN} += "\
	${libdir}/systemd/system/sl1fw.service\
	${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw.service\
	${sysconfdir}/nginx/sites-available/sl1fw\
	${sysconfdir}/nginx/sites-enabled/sl1fw\
	${libdir}/tmpfiles.d/sl1fw-tmpfiles.conf\
	${sysconfdir}/sl1fw/hardware.cfg\
	/usr/bin/main.py\
	/srv/http/intranet\
	/usr/share/scripts\
"

S="${WORKDIR}/git/firmware"
INTRANET=""

inherit setuptools

do_install_append () {
	# Enable sl1fw
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants	
	ln -s ${libdir}/systemd/system/sl1fw.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/sl1fw.service
	
	# Enable nginx site
	install -d ${D}${sysconfdir}/nginx/sites-enabled
	ln -s ${sysconfdir}/nginx/sites-available/sl1fw ${D}${sysconfdir}/nginx/sites-enabled/sl1fw
}